package dev.graffa.scaffold.builders;

import dev.graffa.scaffold.language.JavaFile;
import dev.graffa.scaffold.language.JavaFileWriter;
import dev.graffa.scaffold.language.JavaMethod;
import dev.graffa.scaffold.language.JavaParameter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class NotFoundExceptionClassBuilder {

    private String packageName, sourceFolder;

    public NotFoundExceptionClassBuilder(String packageName, String sourceFolder) {
        this.packageName = packageName;
        this.sourceFolder = sourceFolder;
    }

    public void notFoundClass() throws IOException {
        String sourceCodeFile = sourceFolder + JavaFileWriter.pathSeparator +
                packageName.replace(JavaFileWriter.packageSeparator, JavaFileWriter.pathSeparator) +
                JavaFileWriter.pathSeparator + "NotFoundException.java";

        if (new File(sourceCodeFile).exists()) return;

        String superInterface = "RuntimeException";

        JavaFile notFoundException = new JavaFile();
        notFoundException.setName("NotFoundException");
        notFoundException.setPackageName(packageName);
        notFoundException.setSuperClassName(superInterface);


        JavaMethod constructor = new JavaMethod("NotFoundException", "", List.of("super(message);"));
        constructor.setVisibility("public");
        constructor.setParameters(List.of(new JavaParameter("String", "message")));
        notFoundException.setMethods(List.of(constructor));

        JavaFileWriter.writeFile(notFoundException, sourceCodeFile);
    }

}
