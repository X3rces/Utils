<h1>Utils</h1>
<ul>
<li>FileManager</li>
<li>TimeManager</li>
<li>BytecodeUtils</li>
<li>JarTransformer</li>
</ul>
<h2>FileManager</h2>
The FileManager is used to read and write text files, also holds the method used to read the bytes from a JarEntry
<h2>TimeManager</h2>
The TimeManager can be used to wait for a delay or as a timer which can be used for benchmarking
<h2>BytecodeUtils</h2>
The BytecodeUtils are used for creating getters and setters as well as adding interfaces and converting a ClassNode to a byte[]
<h2>JarTransformer</h2>
The JarTransformer is used to manipulate the classes of a JarFile using the ASM library. To get started initialise the JarTransformer with
<br /><code>JarTransformer jarTransformer = new JarTransformer(new File("test.jar"));</code><br />
then you will need to use
<br /><code>jarTransformer.transformJar()</code><br />
to actually transform the Jar followed by
<br /><code>jarTransformer.createNewJar(new File("new.jar"));</code><br />
to output the new Jar, or you can choose to manipulate/use the ClassNodes further via
<br /><code>jarTransformer.getClasses()</code>
<h4>Transformers</h4>
To actually use the JarTransformer you will need to create Transformer for each class. They'll need to implement the <code>ITransformer</code> interface. An example Transformer can be found under
<code>me.xerces.utils.bytecode.transformer.transformers.ExampleTransformer</code>