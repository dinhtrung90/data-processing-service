package com.vts.data.processing;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.vts.data.processing");

        noClasses()
            .that()
            .resideInAnyPackage("com.vts.data.processing.service..")
            .or()
            .resideInAnyPackage("com.vts.data.processing.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.vts.data.processing.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
