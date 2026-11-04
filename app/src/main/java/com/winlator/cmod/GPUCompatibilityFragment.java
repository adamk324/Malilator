package com.winlator.cmod;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.winlator.cmod.core.GPUCompatibilityProfile;
import com.winlator.cmod.core.GPUInformation;

import java.util.Arrays;
import java.util.Locale;

/** On-demand graphics diagnostics. Values are queried from Android and are never guessed. */
public class GPUCompatibilityFragment extends Fragment {
    private final StringBuilder report = new StringBuilder("Reading graphics capabilities…");
    private TextView reportView;
    private Button shareButton;
    private Button refreshButton;
    private boolean loading;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof AppCompatActivity) {
            androidx.appcompat.app.ActionBar bar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (bar != null) bar.setTitle(R.string.gpu_diagnostics);
        }
        loadReport();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context context = requireContext();
        LinearLayout page = new LinearLayout(context);
        page.setOrientation(LinearLayout.VERTICAL);
        int padding = dp(context, 16);
        page.setPadding(padding, padding, padding, padding);

        TextView heading = new TextView(context);
        heading.setText(R.string.gpu_diagnostics_intro);
        heading.setTextSize(15);
        page.addView(heading, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        reportView = new TextView(context);
        reportView.setTextIsSelectable(true);
        reportView.setTextSize(13);
        reportView.setTypeface(android.graphics.Typeface.MONOSPACE);
        reportView.setPadding(dp(context, 14), dp(context, 14), dp(context, 14), dp(context, 14));
        LinearLayout card = new LinearLayout(context);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundResource(R.drawable.content_dialog_background);
        card.addView(reportView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ScrollView scroll = new ScrollView(context);
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
        scrollParams.topMargin = dp(context, 12);
        scroll.addView(card);
        page.addView(scroll, scrollParams);

        LinearLayout actions = new LinearLayout(context);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        refreshButton = new Button(context);
        refreshButton.setText(R.string.refresh);
        refreshButton.setOnClickListener(v -> loadReport());
        shareButton = new Button(context);
        shareButton.setText(R.string.share_diagnostics);
        shareButton.setEnabled(false);
        shareButton.setOnClickListener(v -> shareReport());
        actions.addView(refreshButton, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        actions.addView(shareButton, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        page.addView(actions, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ThemeManager.applyThemeToView(page, context);
        return page;
    }

    private void loadReport() {
        if (reportView == null || !isAdded() || loading) return;
        loading = true;
        reportView.setText(R.string.gpu_diagnostics_scanning);
        if (refreshButton != null) refreshButton.setEnabled(false);
        if (shareButton != null) shareButton.setEnabled(false);
        Context appContext = requireContext().getApplicationContext();
        Activity activity = getActivity();
        if (activity == null) return;
        new Thread(() -> {
            String result = collectReport(appContext);
            activity.runOnUiThread(() -> {
                if (!isAdded()) return;
                loading = false;
                if (refreshButton != null) refreshButton.setEnabled(true);
                if (reportView != null) reportView.setText(result);
                report.setLength(0);
                report.append(result);
                if (shareButton != null) shareButton.setEnabled(true);
            });
        }, "MalilatorGpuDiagnostics").start();
    }

    private static String collectReport(Context context) {
        String renderer = safeVulkanString(() -> GPUInformation.getRenderer("System", context));
        GPUCompatibilityProfile profile = GPUCompatibilityProfile.fromVulkanDeviceName(renderer);
        String apiVersion = safeVulkanString(() -> GPUInformation.getVulkanVersion("System", context));
        String vendorId;
        try {
            int value = GPUInformation.getVendorID("System", context);
            vendorId = value == 0 ? "Unavailable" : String.format(Locale.ROOT, "0x%04X", value);
        } catch (RuntimeException | LinkageError error) {
            vendorId = "Unavailable";
        }
        String[] extensions;
        try {
            extensions = GPUInformation.enumerateExtensions("System", context);
        } catch (RuntimeException | LinkageError error) {
            extensions = new String[0];
        }
        if (extensions == null) extensions = new String[0];
        Arrays.sort(extensions, String.CASE_INSENSITIVE_ORDER);

        String egl = queryEgl();
        StringBuilder out = new StringBuilder();
        out.append("Malilator graphics diagnostics\n")
                .append("Android: ").append(android.os.Build.VERSION.RELEASE)
                .append(" (API ").append(android.os.Build.VERSION.SDK_INT).append(")\n")
                .append("Device: ").append(android.os.Build.MANUFACTURER).append(' ').append(android.os.Build.MODEL).append("\n\n")
                .append("GPU family: ").append(profile.getFamilyLabel()).append('\n')
                .append("Vulkan device: ").append(profile.deviceName).append('\n')
                .append("Vulkan vendor ID: ").append(vendorId).append('\n')
                .append("Vulkan API version: ").append(apiVersion).append('\n')
                .append("Vulkan driver name/version: Not exposed by the current native query\n")
                .append("Vulkan feature bits and limits: Not exposed by the current native query\n")
                .append("Vulkan device extensions (").append(extensions.length).append("):\n");
        for (String extension : extensions) out.append("  • ").append(extension).append('\n');
        out.append('\n').append(egl)
                .append("\nRecommendation: ").append(profile.getSafeRecommendation()).append('\n')
                .append("This report describes capabilities exposed by the system driver. It does not prove game compatibility.\n");
        return out.toString();
    }

    private interface StringQuery { String get(); }

    private static String safeVulkanString(StringQuery query) {
        try {
            String value = query.get();
            return value == null || value.trim().isEmpty() ? "Unavailable" : value;
        } catch (RuntimeException | LinkageError error) {
            return "Unavailable";
        }
    }

    private static String queryEgl() {
        EGLDisplay display = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
        if (display == null || display == EGL14.EGL_NO_DISPLAY) return "EGL / OpenGL ES: Unavailable (no EGL display)\n";
        int[] version = new int[2];
        if (!EGL14.eglInitialize(display, version, 0, version, 1)) return "EGL / OpenGL ES: Unavailable (eglInitialize failed)\n";

        EGLContext eglContext = EGL14.EGL_NO_CONTEXT;
        EGLSurface surface = EGL14.EGL_NO_SURFACE;
        try {
            EGLConfig config = chooseConfig(display, 0x0040); // EGL_OPENGL_ES3_BIT_KHR
            int clientVersion = config == null ? 2 : 3;
            if (config == null) config = chooseConfig(display, EGL14.EGL_OPENGL_ES2_BIT);
            if (config == null) return "EGL / OpenGL ES: Unavailable (no pbuffer config)\n";

            int[] contextAttrs = {EGL14.EGL_CONTEXT_CLIENT_VERSION, clientVersion, EGL14.EGL_NONE};
            eglContext = EGL14.eglCreateContext(display, config, EGL14.EGL_NO_CONTEXT, contextAttrs, 0);
            if (eglContext == null || eglContext == EGL14.EGL_NO_CONTEXT) {
                if (clientVersion == 3) {
                    clientVersion = 2;
                    config = chooseConfig(display, EGL14.EGL_OPENGL_ES2_BIT);
                    if (config != null) {
                        contextAttrs[1] = clientVersion;
                        eglContext = EGL14.eglCreateContext(display, config, EGL14.EGL_NO_CONTEXT, contextAttrs, 0);
                    }
                }
            }
            if (eglContext == null || eglContext == EGL14.EGL_NO_CONTEXT) return "EGL / OpenGL ES: Unavailable (context creation failed)\n";

            int[] surfaceAttrs = {EGL14.EGL_WIDTH, 1, EGL14.EGL_HEIGHT, 1, EGL14.EGL_NONE};
            surface = EGL14.eglCreatePbufferSurface(display, config, surfaceAttrs, 0);
            if (surface == null || surface == EGL14.EGL_NO_SURFACE || !EGL14.eglMakeCurrent(display, surface, surface, eglContext)) {
                return "EGL / OpenGL ES: Unavailable (pbuffer or make-current failed)\n";
            }

            String vendor = eglString(display, EGL14.EGL_VENDOR);
            String eglVersion = eglString(display, EGL14.EGL_VERSION);
            String glVendor = glString(GLES20.GL_VENDOR);
            String glRenderer = glString(GLES20.GL_RENDERER);
            String glVersion = glString(GLES20.GL_VERSION);
            String glExtensions = glString(GLES20.GL_EXTENSIONS);
            int maxTexture = glLimit(GLES20.GL_MAX_TEXTURE_SIZE);
            return "EGL vendor: " + vendor + "\n"
                    + "EGL version: " + eglVersion + "\n"
                    + "OpenGL ES vendor: " + glVendor + "\n"
                    + "OpenGL ES renderer: " + glRenderer + "\n"
                    + "OpenGL ES version: " + glVersion + "\n"
                    + "Maximum 2D texture size: " + (maxTexture > 0 ? Integer.toString(maxTexture) : "Unavailable") + "\n"
                    + "OpenGL ES extensions: " + (glExtensions.equals("Unavailable") ? "Unavailable" : glExtensions) + "\n";
        } catch (RuntimeException | LinkageError error) {
            return "EGL / OpenGL ES: Unavailable (" + error.getClass().getSimpleName() + ")\n";
        } finally {
            EGL14.eglMakeCurrent(display, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            if (surface != null && surface != EGL14.EGL_NO_SURFACE) EGL14.eglDestroySurface(display, surface);
            if (eglContext != null && eglContext != EGL14.EGL_NO_CONTEXT) EGL14.eglDestroyContext(display, eglContext);
            EGL14.eglTerminate(display);
        }
    }

    private static EGLConfig chooseConfig(EGLDisplay display, int renderableType) {
        int[] attributes = {EGL14.EGL_RENDERABLE_TYPE, renderableType, EGL14.EGL_SURFACE_TYPE, EGL14.EGL_PBUFFER_BIT,
                EGL14.EGL_RED_SIZE, 8, EGL14.EGL_GREEN_SIZE, 8, EGL14.EGL_BLUE_SIZE, 8, EGL14.EGL_NONE};
        EGLConfig[] configs = new EGLConfig[1];
        int[] count = new int[1];
        return EGL14.eglChooseConfig(display, attributes, 0, configs, 0, 1, count, 0) && count[0] > 0 ? configs[0] : null;
    }

    private static String eglString(EGLDisplay display, int name) {
        String value = EGL14.eglQueryString(display, name);
        return value == null || value.isEmpty() ? "Unavailable" : value;
    }

    private static String glString(int name) {
        String value = GLES20.glGetString(name);
        return value == null || value.isEmpty() ? "Unavailable" : value;
    }

    private static int glLimit(int name) {
        int[] value = new int[1];
        GLES20.glGetIntegerv(name, value, 0);
        return value[0];
    }

    private void shareReport() {
        if (getContext() == null) return;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Malilator graphics diagnostics");
        intent.putExtra(Intent.EXTRA_TEXT, report.toString());
        startActivity(Intent.createChooser(intent, getString(R.string.share_diagnostics)));
    }

    private static int dp(Context context, int value) {
        return Math.round(value * context.getResources().getDisplayMetrics().density);
    }
}
