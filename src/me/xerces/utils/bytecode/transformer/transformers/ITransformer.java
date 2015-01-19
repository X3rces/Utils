package me.xerces.utils.bytecode.transformer.transformers;

import org.objectweb.asm.tree.ClassNode;

/**
 * @author Xerces
 * @since 19/01/2015
 */
public interface ITransformer {

    public ClassNode transform(ClassNode classNode);

}
