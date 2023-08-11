package com.auth.test.model;

import com.auth.test.model.type.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.sql.Timestamp;

@MappedSuperclass
@Data
public abstract class BaseModel {

    private Timestamp creationTs;
    private Timestamp modificationTs;
    @Enumerated(EnumType.STRING)
    private Status status;
}
