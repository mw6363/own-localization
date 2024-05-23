package com.mw6363.ol2

import android.graphics.text.LineBreaker
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.mw6363.ol2.databinding.ActivityMainBinding
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var input: InputStream
   // private lateinit var dbg_input: String

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // read conf-file and show content

        val file = File(filesDir.toString()+File.separator+"settings.xml")

        if (file.exists()) {
            //dbg_input = file.reader(Charsets.UTF_8).readText()
            input = file.inputStream()
                    }
        else {
            input = assets.open("settings.xml")
                   }

        //Toast.makeText(this,file.absoluteFile.toString(), Toast.LENGTH_LONG).show()

        var xmlDoc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input)
        xmlDoc.documentElement.normalize()

        var setting_list: NodeList = xmlDoc.getElementsByTagName("setting")
        var setting_node: Node = setting_list.item(0)
        var setting_elements: Element = setting_node as Element

        var device = setting_elements.getElementsByTagName("DeviceName").item(0).textContent
        var protocol = setting_elements.getElementsByTagName("ServerProtocol").item(0).textContent
        var serverURL = setting_elements.getElementsByTagName("ServerURL").item(0).textContent
        var serverport = setting_elements.getElementsByTagName("ServerPort").item(0).textContent
        var interval = setting_elements.getElementsByTagName("SendInterval").item(0).textContent

        var tx_device: EditText = findViewById(R.id.Device)
        var tx_protocol: EditText = findViewById(R.id.Protocol)
        var tx_url: EditText = findViewById(R.id.ServerURL)
        var tx_port: EditText =  findViewById(R.id.Serverport)
        var tx_interval: EditText =  findViewById(R.id.Interval)

        var tx_debug: EditText =  findViewById(R.id.debug)

        tx_device.setText(device.toString())
        tx_protocol.setText(protocol.toString())
        tx_url.setText(serverURL.toString())
        tx_port.setText(serverport.toString())
        tx_interval.setText(interval.toString())

        //tx_debug.setText(dbg_input)
        input.close()

        binding.fab.setOnClickListener { view ->
            // write changes to conf-file
            // Create a new XML document
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val doc = builder.newDocument()

            // Add nodes, content
            val rootElement = doc.createElement("setting")
            doc.appendChild(rootElement)

            val wr_device = doc.createElement("DeviceName")
            wr_device.textContent = tx_device.text.toString()
            rootElement.appendChild(wr_device)

            val wr_prot = doc.createElement("ServerProtocol")
            wr_prot.textContent = tx_protocol.text.toString()
            rootElement.appendChild(wr_prot)

            val wr_url = doc.createElement("ServerURL")
            wr_url.textContent = tx_url.text.toString()
            rootElement.appendChild(wr_url)

            val wr_port = doc.createElement("ServerPort")
            wr_port.textContent = tx_port.text.toString()
            rootElement.appendChild(wr_port)

            val wr_interval = doc.createElement("SendInterval")
            wr_interval.textContent = tx_interval.text.toString()
            rootElement.appendChild(wr_interval)

            // Save the document to an XML file
            val transformerFactory = TransformerFactory.newInstance()
            val transformer = transformerFactory.newTransformer()

            doc.documentElement.normalize()

            val source = DOMSource(doc)
            val result = StreamResult(file)

            transformer.transform(source, result)

            //Toast.makeText(this,tx_debug.text, Toast.LENGTH_LONG).show()
            Toast.makeText(this,"Save successfully", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}