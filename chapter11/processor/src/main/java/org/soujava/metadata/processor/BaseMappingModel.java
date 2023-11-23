package org.soujava.metadata.processor;

import java.time.LocalDateTime;

abstract class BaseMappingModel {

    public LocalDateTime getNow() {
        return LocalDateTime.now();
    }
}
