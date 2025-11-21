package com.fatec.glab.mapper;

import com.fatec.glab.dto.software.SoftwareRequestDTO;
import com.fatec.glab.dto.software.SoftwareRequestUpdateDTO;
import com.fatec.glab.dto.software.SoftwareResponseDTO;
import com.fatec.glab.model.Software;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SoftwareMapper {

    SoftwareResponseDTO toDTO(Software software);

    List<SoftwareResponseDTO> toDTO(List<Software> softwareList);

    @Mapping(target = "id", ignore = true)
    Software toEntity(SoftwareRequestDTO softwareRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateFromDTO(SoftwareRequestUpdateDTO softwareRequestUpdateDTO, @MappingTarget Software software);


}
