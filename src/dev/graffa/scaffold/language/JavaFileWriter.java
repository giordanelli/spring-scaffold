package dev.graffa.scaffold.language;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class JavaFileWriter {
    public static final String packageSeparator = ".", pathSeparator = "/", fileExtension = ".java", space = " ";
    public static final String packageInstruction = "package", importInstruction = "import", endLine = ";", newLine =
            "\n";

    public static final String classVisibility = "public", extendsInstruction = "extends", implementsInstruction =
            "implements", openSection = "{", closeSection = "}", openParameters = "(", closeParameters = ")", comma =
            ",", indent = "\t";


    public static void writeFile(JavaFile javaFile, String sourceCodeFile) throws IOException {

        File f = new File(sourceCodeFile);
        if (f.exists())
            throw new IllegalStateException("The class already exists.");
        if (!Files.exists(Paths.get(f.getParent())))
            Files.createDirectory(Paths.get(f.getParent()));
        BufferedWriter writer = new BufferedWriter(new FileWriter(sourceCodeFile));
        writer.write(print(javaFile));
        writer.close();
    }

    protected static String print(JavaFile javaFile) {
        return packageInstruction(javaFile) + importInstruction(javaFile) + annotations(javaFile) + declaration(javaFile) + objectVariables(javaFile) + methods(javaFile) + closeFile();
    }

    protected static String annotations(JavaFile javaFile) {
        if (javaFile.getAnnotations() == null) return "";
        return javaFile.getAnnotations().stream().collect(Collectors.joining(newLine)) + newLine;
    }

    protected static String packageInstruction(JavaFile javaFile) {
        return packageInstruction + space + javaFile.getPackageName() + endLine + newLine + newLine;
    }

    protected static String importInstruction(JavaFile javaFile) {
        if (javaFile.getImports() == null) return "";
        return javaFile.getImports().stream().map((packageName) -> importInstruction + space + packageName + endLine)
                .collect(Collectors.joining(newLine)) + newLine + newLine;
    }

    protected static String declaration(JavaFile javaFile) {
        String ret = classVisibility + space + javaFile.getType().name + space + javaFile.getName();

        if (javaFile.getSuperClassName() != null)
            ret += space + extendsInstruction + space + javaFile.getSuperClassName();

        if (javaFile.getInterfaceName() != null)
            ret += space + implementsInstruction + space + javaFile.getInterfaceName();

        return ret + openSection + newLine;
    }

    protected static String closeFile() {
        return closeSection;
    }

    protected static String objectVariables(JavaFile javaFile) {
        if (javaFile.getVariables() == null) return "";
        return javaFile.getVariables().stream().map((variable) -> {
            return indent + variable.getAnnotation() + newLine + indent + variable.getVisibility() + space + variable.getType() + space + variable.getName();
        }).collect(Collectors.joining(endLine + newLine)) + endLine + newLine;
    }

    protected static String methods(JavaFile javaFile) {
        if (javaFile.getMethods() == null || javaFile.getMethods().size() == 0) return "";
        return javaFile.getMethods().stream().map((method) -> {
            String toPrint = "";
            if (method.getAnnotations() != null)
                toPrint += indent + method.getAnnotations().stream().collect(Collectors.joining(";\n")) + newLine;
            toPrint += indent + method.getVisibility() + space + method.getReturnObject() + space
                    + method.getName() + openParameters;
            if (method.getParameters() != null)
                toPrint += method.getParameters().stream().map((param -> param.getAnnotation() + space + param.getType() + space + param.getName()))
                        .collect(Collectors.joining(comma));
            toPrint += closeParameters + openSection + newLine;
            if (method.getInstructions() != null)
                toPrint += indent + indent + method.getInstructions().stream().collect(Collectors.joining(newLine + indent + indent));

            return toPrint + newLine + indent + closeSection;
        }).collect(Collectors.joining(newLine + newLine)) + newLine + newLine;
    }

}
