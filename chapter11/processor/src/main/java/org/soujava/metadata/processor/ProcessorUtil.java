package org.soujava.metadata.processor;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import static java.util.Locale.ENGLISH;

final class ProcessorUtil {

    private ProcessorUtil() {
    }

    static String getPackageName(TypeElement classElement) {
        return ((PackageElement) classElement.getEnclosingElement()).getQualifiedName().toString();
    }

    static String getSimpleNameAsString(Element element) {
        return element.getSimpleName().toString();
    }

    static String capitalize(String name) {
        return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }

    static boolean isTypeElement(Element element) {
        return element instanceof TypeElement;
    }
}
