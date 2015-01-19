package me.xerces.utils.bytecode.transformer;

import me.xerces.utils.bytecode.transformer.transformers.ITransformer;
import me.xerces.utils.managers.FileManager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import static me.xerces.utils.bytecode.BytecodeUtils.*;

/**
 * @author Xerces
 * @since 19/01/2015
 */
public class JarTransformer{

    private JarFile jarFile;

    private HashMap<String, ITransformer> transformerMap = new HashMap<>();

    private List<ClassNode> newClasses = new ArrayList<>();

    /**
     * JarTransformer is used to transform a {@link java.util.jar.JarFile}
     * @param file the {@link java.util.jar.JarFile} to transform
     * @throws IOException
     */
    public JarTransformer(File file) throws IOException
    {
        if(file.exists())
        {
            this.jarFile = new JarFile(file);
        } else {
            throw new IOException("JarFile does not exists.");
        }
    }

    /**
     * Add a {@link me.xerces.utils.bytecode.transformer.transformers.ITransformer} to transform a class
     * @param className the internal name of the class, the path can either be the internal class name or the external class name
     * @param iTransformer the {@link me.xerces.utils.bytecode.transformer.transformers.ITransformer} to add
     */
    public void addTransformer(String className, ITransformer iTransformer)
    {
        if(className.contains(".") && !className.contains("/"))
        {
            this.transformerMap.put(className.replaceAll(".", "/"), iTransformer);
        } else {
            this.transformerMap.put(className, iTransformer);
        }
    }

    /**
     * Transform the {@link java.util.jar.JarFile} with the specified
     * {@link me.xerces.utils.bytecode.transformer.transformers.ITransformer} added via the {@link #addTransformer(String, me.xerces.utils.bytecode.transformer.transformers.ITransformer)}
     * @throws IOException
     */
    public void transformJar() throws IOException
    {
        Enumeration<JarEntry> jarIterator = jarFile.entries();
        while(jarIterator.hasMoreElements())
        {
            JarEntry jarEntry = jarIterator.nextElement();
            if(jarEntry.getName().endsWith(".class"))
            {
                ITransformer iTransformer;
                if((iTransformer = transformerMap.get(jarEntry.getName())) != null)
                {
                    ClassReader classReader = new ClassReader(FileManager.getBytesFromEntry(jarFile, jarEntry));
                    ClassNode classNode = new ClassNode();
                    classReader.accept(classNode, 0);
                    newClasses.add(iTransformer.transform(classNode));
                } else {
                    ClassReader classReader = new ClassReader(FileManager.getBytesFromEntry(jarFile, jarEntry));
                    ClassNode classNode = new ClassNode();
                    classReader.accept(classNode, 0);
                    newClasses.add(classNode);
                }
            }
        }
    }

    /**
     * Create the {@link java.util.jar.JarFile}
     * @param newJarFile the {@link java.io.File} to output the new {@link java.util.jar.JarFile} to, will be created if it does not exist.
     * @throws IOException
     */
    public void createNewJar(File newJarFile) throws IOException
    {
        if(newClasses.size() <= 0)
        {
            throw new IOException("Cannot create a new JarFile if you have not transformed the old one.");
        }
        if(!newJarFile.exists())
        {
            newJarFile.mkdirs();
            newJarFile.createNewFile();
        }
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(newJarFile), jarFile.getManifest());
        Enumeration<JarEntry> jarIterator = jarFile.entries();
        while(jarIterator.hasMoreElements())
        {
            JarEntry jarEntry = jarIterator.nextElement();
            if(!jarEntry.getName().endsWith(".class") && !jarEntry.getName().equals("MANIFEST.MF"))
            {
                jarOutputStream.putNextEntry(jarEntry);
                jarOutputStream.write(FileManager.getBytesFromEntry(jarFile, jarEntry));
            }
        }
        for(ClassNode classNode : newClasses)
        {
            JarEntry jarEntry = new JarEntry(classNode.name + ".class");
            jarOutputStream.putNextEntry(jarEntry);
            jarOutputStream.write(classNodeToBytes(classNode));
        }
        jarOutputStream.flush();
        jarOutputStream.close();
    }

    /**
     * Get the maniuplated {@link org.objectweb.asm.tree.ClassNode}, make sure you have called {@link #transformJar()} before you call this.
     * @return the {@link java.util.ArrayList} of {@link org.objectweb.asm.tree.ClassNode}
     */
    public List<ClassNode> getClasses()
    {
        return newClasses;
    }

}
