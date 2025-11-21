package com.fatec.glab.mapper;

import com.fatec.glab.dto.software.SoftwareRequestDTO;
import com.fatec.glab.dto.software.SoftwareRequestUpdateDTO;
import com.fatec.glab.dto.software.SoftwareResponseDTO;
import com.fatec.glab.model.Software;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SoftwareMapper {

    SoftwareResponseDTO toDTO(Software software);

    List<SoftwareResponseDTO> toDTO(List<Software> softwareList);

    Software toEntity(SoftwareRequestDTO softwareRequestDTO);

    void updateFromDTO(SoftwareRequestUpdateDTO softwareRequestUpdateDTO, Software software);


}
