package me.xerces.utils.bytecode;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

/**
 * @author Xerces
 * @since 18/01/2015
 */
public class BytecodeUtils implements Opcodes{

    /**
     * Generates a getter method for the specified field
     * @param fieldName the name of the field
     * @param className the internal class name
     * @param fieldDesc the field descriptor
     * @param methodName the name of the method to create
     * @return the method as a {@link org.objectweb.asm.tree.MethodNode}
     */
    public static MethodNode generateGetter(String methodName, String fieldName, String className, String fieldDesc)
    {
        MethodNode methodNode = new MethodNode(ACC_PUBLIC, methodName, "()" + fieldDesc, null, null);
        methodNode.instructions.insert(new VarInsnNode(ALOAD, 0));
        methodNode.instructions.insert(new FieldInsnNode(GETFIELD, className, fieldName, fieldDesc));
        methodNode.instructions.insert(new InsnNode(Type.getType(fieldDesc).getOpcode(IRETURN)));
        return methodNode;
    }

    /**
     * Generates a setter method for the specified field
     * @param fieldName the name of the field
     * @param className the internal class name
     * @param fieldDesc the field descriptor
     * @param methodName the name of the method to create
     * @return the method as a {@link org.objectweb.asm.tree.MethodNode}
     */
    public static MethodNode generateSetter(String methodName, String fieldName, String className, String fieldDesc)
    {
        MethodNode methodNode = new MethodNode(ACC_PUBLIC, methodName, "(" + fieldDesc + ")V", null, null);
        methodNode.instructions.insert(new VarInsnNode(ALOAD, 0));
        methodNode.instructions.insert(new VarInsnNode(Type.getType(fieldDesc).getOpcode(ILOAD), 1));
        methodNode.instructions.insert(new FieldInsnNode(PUTFIELD, className, fieldName, fieldDesc));
        methodNode.instructions.insert(new InsnNode(RETURN));
        return methodNode;
    }

    /**
     * Adds interfaces to a class
     * @param classNode the {@link org.objectweb.asm.tree.ClassNode} to add the interfaces too
     * @param interfaces a {@link java.lang.Class} array of the interfaces to add
     */
    public static void addInterfaces(ClassNode classNode, Class<?>[] interfaces)
    {
        for(Class<?> interfaceClass : interfaces)
        {
            if(interfaceClass.isInterface())
            {
                classNode.interfaces.add(interfaceClass.getName().replaceAll(".", "/"));
            }
        }
    }

    /**
     * Convert a ClassNode to a byte array
     * @param classNode the {@link org.objectweb.asm.tree.ClassNode} to convert to a byte array
     * @return the byte array
     */
    public static byte[] classNodeToBytes(ClassNode classNode)
    {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

}
