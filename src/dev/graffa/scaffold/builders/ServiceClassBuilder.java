package dev.graffa.scaffold.builders;

import dev.graffa.scaffold.language.*;

import java.io.IOException;
import java.util.List;

public class ServiceClassBuilder {
    private String entityClassName, idClassName, entityPackageName, serviceClassName, servicePackage,
            repositoryPackageName, repositoryClassName, sourceFolder;

    public ServiceClassBuilder(String entityClassName, String idClassName, String entityPackageName,
                               String serviceClassName, String servicePackage, String repositoryPackageName,
                               String repositoryClassName, String sourceFolder) {
        this.entityClassName = entityClassName;
        this.idClassName = idClassName;
        this.entityPackageName = entityPackageName;
        this.serviceClassName = serviceClassName;
        this.servicePackage = servicePackage;
        this.repositoryPackageName = repositoryPackageName;
        this.repositoryClassName = repositoryClassName;
        this.sourceFolder = sourceFolder;
    }

    public void serviceClass() throws IOException {
        List<String> imports = List.of("org.springframework.stereotype.Service",
                "org.springframework.beans.factory.annotation.Autowired", "java.util.Optional", "java.util.List",
                entityPackageName + JavaFileWriter.packageSeparator + entityClassName,
                repositoryPackageName + JavaFileWriter.packageSeparator + repositoryClassName);
        JavaFile service = new JavaFile();
        service.setName(serviceClassName);
        service.setPackageName(servicePackage);
        service.setImports(imports);
        service.setAnnotations(List.of("@Service"));

        service.setVariables(List.of(new JavaObjectVariable("protected", repositoryClassName, "repository",
                "@Autowired")));

        //all
        String allInstr = "return repository.findAll();";
        JavaMethod all = new JavaMethod("List<" + entityClassName + ">", "getAll", List.of(allInstr));
        all.setVisibility("public");

        //byId
        String findByIdInstr = "return repository.findById(id);";
        JavaMethod byId = new JavaMethod("public", "Optional<" + entityClassName + ">", "findById",
                List.of(new JavaParameter(idClassName, "id")), List.of(findByIdInstr));

        //delete
        String deleteByIdInstr = "repository.deleteById(id);";
        JavaMethod deleteById = new JavaMethod("public", "void", "deleteById",
                List.of(new JavaParameter(idClassName, "id")), List.of(deleteByIdInstr));

        //save
        String saveInstr = "return repository.save(entity);";
        JavaMethod save = new JavaMethod("public", entityClassName, "save",
                List.of(new JavaParameter(entityClassName, "entity")), List.of(saveInstr));

        service.setMethods(List.of(all, byId, deleteById, save));

        String sourceCodeFile = sourceFolder + JavaFileWriter.pathSeparator +
                servicePackage.replace(JavaFileWriter.packageSeparator, JavaFileWriter.pathSeparator) +
                JavaFileWriter.pathSeparator + serviceClassName + ".java";
        JavaFileWriter.writeFile(service, sourceCodeFile);
    }
}
