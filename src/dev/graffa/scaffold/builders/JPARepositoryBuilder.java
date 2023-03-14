package dev.graffa.scaffold.builders;

import dev.graffa.scaffold.language.JavaFile;
import dev.graffa.scaffold.language.JavaFileWriter;

import java.io.IOException;
import java.util.List;

public class JPARepositoryBuilder {

    private String entityClassName, idClassName, entityPackageName, repositoryClassName, repositoryPackage,
            sourceFolder;

    public JPARepositoryBuilder(String entityClassName, String idClassName, String entityPackageName,
                                String repositoryClassName, String repositoryPackage, String sourceFolder) {
        this.entityClassName = entityClassName;
        this.idClassName = idClassName;
        this.entityPackageName = entityPackageName;
        this.repositoryClassName = repositoryClassName;
        this.repositoryPackage = repositoryPackage;
        this.sourceFolder = sourceFolder;
    }

    public void repositoryClass() throws IOException {
        String superInterface = "JpaRepository<" + entityClassName + ", " + idClassName + ">";

        List<String> imports = List.of("org.springframework.data.jpa.repository.JpaRepository",
                entityPackageName + JavaFileWriter.packageSeparator + entityClassName);
        JavaFile repo = new JavaFile();
        repo.setName(repositoryClassName);
        repo.setPackageName(repositoryPackage);
        repo.setSuperClassName(superInterface);
        repo.setImports(imports);
        repo.setType(JavaFile.TYPE.Interface);

        String sourceCodeFile = sourceFolder + JavaFileWriter.pathSeparator +
                repositoryPackage.replace(JavaFileWriter.packageSeparator, JavaFileWriter.pathSeparator) +
                JavaFileWriter.pathSeparator + repositoryClassName + ".java";
        JavaFileWriter.writeFile(repo, sourceCodeFile);
    }
}
