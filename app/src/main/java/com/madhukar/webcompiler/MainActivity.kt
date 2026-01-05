package com.madhukar.webcompiler

import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var editor: EditText
    private lateinit var preview: WebView
    private lateinit var lineNumbers: TextView

    private var currentTab = 0
    private var lastTab = 0
    private var fontSize = 14f

    private var html = "<h1>Hello HTML</h1>"
    private var css = "body{font-family:sans-serif;}"
    private var js = "console.log('JS ready');"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Splash ke baad main theme
        setTheme(R.style.Theme_WebCompiler)

        setContentView(R.layout.activity_main)

        // ---- Views ----
        editor = findViewById(R.id.editor)
        preview = findViewById(R.id.preview)
        lineNumbers = findViewById(R.id.lineNumbers)

        val tabs = findViewById<TabLayout>(R.id.bottomTabs)
        val runBtn = findViewById<FloatingActionButton>(R.id.runBtn)

        // 🔧 IMPORTANT FIX (ImageButton, NOT TextView)
        val plusBtn = findViewById<ImageButton>(R.id.btnPlus)
        val minusBtn = findViewById<ImageButton>(R.id.btnMinus)

        // ---- WebView safe setup ----
        preview.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
        preview.webViewClient = WebViewClient()

        // ---- Tabs ----
        tabs.removeAllTabs()
        tabs.addTab(tabs.newTab().setText("HTML"))
        tabs.addTab(tabs.newTab().setText("CSS"))
        tabs.addTab(tabs.newTab().setText("JS"))
        tabs.addTab(tabs.newTab().setText("PREVIEW"))

        // ---- Load saved code ----
        loadSaved()
        showEditor(html, false)

        // ---- Tab change ----
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                saveCurrent()
                lastTab = currentTab
                currentTab = tab.position

                val slideLeft = currentTab > lastTab

                when (currentTab) {
                    0 -> showEditor(html, slideLeft)
                    1 -> showEditor(css, slideLeft)
                    2 -> showEditor(js, slideLeft)
                    3 -> showPreview(slideLeft)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // ---- Floating RUN ----
        runBtn.setOnClickListener {
            saveCurrent()
            lastTab = currentTab
            currentTab = 3
            showPreview(true)
            tabs.getTabAt(3)?.select()
        }

        // ---- Zoom IN ----
        plusBtn.setOnClickListener {
            fontSize += 2f
            editor.textSize = fontSize
        }

        // ---- Zoom OUT ----
        minusBtn.setOnClickListener {
            if (fontSize > 10f) {
                fontSize -= 2f
                editor.textSize = fontSize
            }
        }
    }

    // ---------------- Animation ----------------

    private fun slideIn(view: View, fromLeft: Boolean) {
        val width = resources.displayMetrics.widthPixels.toFloat()
        view.translationX = if (fromLeft) width else -width
        view.alpha = 1f
        view.animate()
            .translationX(0f)
            .setDuration(220)
            .start()
    }

    // ---------------- UI ----------------

    private fun showEditor(code: String, slideLeft: Boolean) {
        preview.visibility = View.GONE
        editor.visibility = View.VISIBLE

        val highlighted = when (currentTab) {
            0 -> SyntaxHighlighter.highlightHTML(code)
            1 -> SyntaxHighlighter.highlightCSS(code)
            2 -> SyntaxHighlighter.highlightJS(code)
            else -> code
        }

        editor.setText(highlighted)
        editor.setSelection(editor.text.length)
        updateLines(code)
        slideIn(editor, slideLeft)
    }

    private fun showPreview(slideLeft: Boolean) {
        editor.visibility = View.GONE
        preview.visibility = View.VISIBLE

        val finalHtml = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>$css</style>
            </head>
            <body>
                $html
                <script>$js</script>
            </body>
            </html>
        """.trimIndent()

        preview.loadDataWithBaseURL(null, finalHtml, "text/html", "utf-8", null)
        slideIn(preview, slideLeft)
    }

    private fun updateLines(text: String) {
        val lines = text.split("\n").size
        val sb = StringBuilder()
        for (i in 1..lines) sb.append(i).append("\n")
        lineNumbers.text = sb.toString()
    }

    private fun saveCurrent() {
        when (currentTab) {
            0 -> html = editor.text.toString()
            1 -> css = editor.text.toString()
            2 -> js = editor.text.toString()
        }

        getSharedPreferences("code", Context.MODE_PRIVATE)
            .edit()
            .putString("html", html)
            .putString("css", css)
            .putString("js", js)
            .apply()
    }

    private fun loadSaved() {
        val sp = getSharedPreferences("code", Context.MODE_PRIVATE)
        html = sp.getString("html", html) ?: html
        css = sp.getString("css", css) ?: css
        js = sp.getString("js", js) ?: js
    }
}
