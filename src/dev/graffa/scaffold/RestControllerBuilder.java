package dev.graffa.scaffold;

import dev.graffa.scaffold.builders.JPARepositoryBuilder;
import dev.graffa.scaffold.builders.NotFoundExceptionClassBuilder;
import dev.graffa.scaffold.builders.RestControllerClassBuilder;
import dev.graffa.scaffold.builders.ServiceClassBuilder;
import dev.graffa.scaffold.language.JavaFileWriter;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class RestControllerBuilder {
    static final String YES = "Y", NO = "N";
    protected static String repositoryClassName, repositoryPackage, entityClassName, idClassName, entityPackageName,
            basePackageName, serviceClassName, servicePackage, controllerClassName, controllerPackage, baseUrl,
            sourceFolder,notFoundExceptionPackage;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String cwd = Paths.get(".").toAbsolutePath().normalize().toString();
        System.out.println("Current working directory is " + cwd);
        sourceFolder = readLine("Enter your source code folder");//"src/main/java";
        basePackageName = readLine("Enter your base package name");//"com.rgsoftware.twogethere";
        System.out.println("--- ENTITY ---");
        entityClassName = readLine("Enter your entity class name");//"Collection";
        entityPackageName = basePackageName + JavaFileWriter.packageSeparator + "entities";
        idClassName = readLine("Enter your entity ID class name");//"Long";
        System.out.println("--- REPOSITORY ---");
        boolean repositoryAlreadyExists = getBoolean("Does the Repository already exists (Y/N)?");//false;
        repositoryClassName = entityClassName + "Repository";
        repositoryPackage = basePackageName + JavaFileWriter.packageSeparator + "repositories";
        System.out.println("--- SERVICE ---");
        boolean serviceAlreadyExists = getBoolean("Does the Service already exists (Y/N)?");//false;
        serviceClassName = entityClassName + "Service";
        servicePackage = basePackageName + JavaFileWriter.packageSeparator + "services";
        System.out.println("--- CONTROLLER ---");
        controllerClassName = entityClassName + "RestController";
        controllerPackage = basePackageName + JavaFileWriter.packageSeparator + "controllers.rest";
        notFoundExceptionPackage=basePackageName + ".exceptions";
        baseUrl = entityClassName.toLowerCase();

        if (!repositoryAlreadyExists)
            new JPARepositoryBuilder(entityClassName, idClassName, entityPackageName, repositoryClassName,
                    repositoryPackage, sourceFolder).repositoryClass();

        if (!serviceAlreadyExists)
            new ServiceClassBuilder(entityClassName, idClassName, entityPackageName, serviceClassName, servicePackage
                    , repositoryPackage, repositoryClassName, sourceFolder).serviceClass();

        new NotFoundExceptionClassBuilder(notFoundExceptionPackage, sourceFolder).notFoundClass();

        new RestControllerClassBuilder(entityClassName, idClassName, entityPackageName, servicePackage,
                serviceClassName,
                baseUrl, controllerClassName, controllerPackage, notFoundExceptionPackage,sourceFolder).controllerClass();
    }

    private static boolean getBoolean(String help) {
        System.out.println(help);
        String toRead = scanner.nextLine();
        while (!toRead.equalsIgnoreCase(YES) && !toRead.equalsIgnoreCase(NO)) {
            System.out.println(help);
            toRead = scanner.nextLine();
        }
        if (toRead.equalsIgnoreCase(YES)) return true;
        return false;
    }

    private static String readLine(String help) {
        System.out.println(help);
        return scanner.nextLine();
    }
}
