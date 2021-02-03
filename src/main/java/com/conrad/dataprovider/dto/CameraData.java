package com.conrad.dataprovider.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CameraData {

    private String carNumber;
    private Integer actualSpeed;
    private Timestamp incidentTimestamp;
    private UUID cameraUuid;
    private CarType carType;
}
