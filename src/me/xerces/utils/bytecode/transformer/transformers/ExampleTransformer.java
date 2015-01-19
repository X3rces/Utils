package me.xerces.utils.bytecode.transformer.transformers;

import org.objectweb.asm.tree.*;

import java.util.List;

import static me.xerces.utils.bytecode.BytecodeUtils.*;

/**
 * @author Xerces
 * @since 19/01/2015
 */
public class ExampleTransformer implements ITransformer{

    public ClassNode transform(ClassNode classNode) {
        for(MethodNode methodNode : (List<MethodNode>)classNode.methods)
        {
            AbstractInsnNode firstNode = methodNode.instructions.getFirst();
            methodNode.instructions.insertBefore(firstNode, new FieldInsnNode(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream"));
            methodNode.instructions.insertBefore(firstNode, new LdcInsnNode(methodNode.name + " was invoked."));
            methodNode.instructions.insertBefore(firstNode, new MethodInsnNode(INVOKEVIRTUAL, "java/io/PrintStream", "println", "()V", false));
        }
        return classNode;
    }
}
