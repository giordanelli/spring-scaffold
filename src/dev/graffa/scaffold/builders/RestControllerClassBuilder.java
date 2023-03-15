package dev.graffa.scaffold.builders;

import dev.graffa.scaffold.language.*;

import java.io.IOException;
import java.util.List;

public class RestControllerClassBuilder {

    private String entityClassName, idClassName, entityPackageName, servicePackage, serviceClassName, baseUrl,
            controllerClassName, controllerPackage, notFoundExceptionPackage, sourceFolder;

    public RestControllerClassBuilder(String entityClassName, String idClassName, String entityPackageName,
                                      String servicePackage, String serviceClassName, String baseUrl,
                                      String controllerClassName, String controllerPackage,
                                      String notFoundExceptionPackage,String sourceFolder) {
        this.entityClassName = entityClassName;
        this.idClassName = idClassName;
        this.entityPackageName = entityPackageName;
        this.servicePackage = servicePackage;
        this.serviceClassName = serviceClassName;
        this.baseUrl = baseUrl;
        this.controllerClassName = controllerClassName;
        this.controllerPackage = controllerPackage;
        this.notFoundExceptionPackage=notFoundExceptionPackage;
        this.sourceFolder = sourceFolder;
    }

    public void controllerClass() throws IOException {
        List<String> imports = List.of(notFoundExceptionPackage+".NotFoundException",
                "org.springframework.beans.factory.annotation.Autowired", "org.springframework.http.ResponseEntity",
                "java.util.List", "org.springframework.security.config" +
                        ".annotation.method.configuration.EnableMethodSecurity",
                "org.springframework.web.bind.annotation.*",
                entityPackageName + JavaFileWriter.packageSeparator + entityClassName,
                servicePackage + JavaFileWriter.packageSeparator + serviceClassName);

        JavaFile controller = new JavaFile();
        controller.setAnnotations(List.of("@RestController", "@EnableMethodSecurity", "@RequestMapping(\"" + baseUrl +
                "\")"));
        controller.setName(controllerClassName);
        controller.setPackageName(controllerPackage);
        controller.setImports(imports);
        controller.setVariables(List.of(new JavaObjectVariable("protected", serviceClassName, "service",
                "@Autowired")));
        // method all

        String allInstr = "return service.getAll();";
        JavaMethod all = new JavaMethod("List<" + entityClassName + ">", "all", List.of(allInstr));
        all.setVisibility("public");
        all.setAnnotations(List.of("@GetMapping"));

        // method one
        String oneInstr =
                "return service.findById(id).orElseThrow(() -> new NotFoundException(\"Could not find " + entityClassName + " with ID \" + id));";
        JavaMethod one = new JavaMethod(entityClassName, "one", List.of(oneInstr));
        one.setVisibility("public");
        one.setAnnotations(List.of("@GetMapping(\"/{id}\")"));
        one.setParameters(List.of(new JavaParameter(idClassName, "id", "@PathVariable")));

        // method post
        String postInstr = "return service.save(newEntity);";
        JavaMethod post = new JavaMethod(entityClassName, "post", List.of(postInstr));
        post.setVisibility("public");
        post.setParameters(List.of(new JavaParameter(entityClassName, "newEntity", "@RequestBody")));
        post.setAnnotations(List.of("@PostMapping"));

        // method put

        String putInstr1 =
                "service.findById(id).orElseThrow(() -> new NotFoundException(\"Could not find " + entityClassName +
                        " with ID \" + id));";
        String putInstr2 = "updatedEntity.setId(id);";
        String putInstr3 = "return service.save(updatedEntity);";
        JavaMethod put = new JavaMethod(entityClassName, "put", List.of(putInstr1, putInstr2, putInstr3));
        put.setVisibility("public");
        put.setParameters(List.of(new JavaParameter(entityClassName, "updatedEntity", "@RequestBody"),
                new JavaParameter(idClassName, "id", "@PathVariable")));
        put.setAnnotations(List.of("@PutMapping(\"/{id}\")"));

        // method delete
        String delInstr1 = "service.deleteById(id);";
        String delInstr2 = "return ResponseEntity.noContent().build();";

        JavaMethod delete = new JavaMethod("ResponseEntity", "delete", List.of(delInstr1, delInstr2));
        delete.setVisibility("public");
        delete.setParameters(List.of(new JavaParameter(idClassName, "id", "@PathVariable")));
        delete.setAnnotations(List.of("@DeleteMapping(\"/{id}\")"));

        controller.setMethods(List.of(all, one, put, post, delete));


        String sourceCodeFile = sourceFolder + JavaFileWriter.pathSeparator +
                controllerPackage.replace(JavaFileWriter.packageSeparator, JavaFileWriter.pathSeparator) +
                JavaFileWriter.pathSeparator + controllerClassName + ".java";
        JavaFileWriter.writeFile(controller, sourceCodeFile);

    }
}
