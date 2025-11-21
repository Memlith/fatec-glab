package com.fatec.glab.mapper;

import com.fatec.glab.dto.equipment.EquipmentRequestDTO;
import com.fatec.glab.dto.equipment.EquipmentRequestUpdateDTO;
import com.fatec.glab.dto.equipment.EquipmentResponseDTO;
import com.fatec.glab.model.Equipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {

    EquipmentResponseDTO toDTO(Equipment equipment);

    List<EquipmentResponseDTO> toDTO(List<Equipment> equipmentList);

    @Mapping(target = "id", ignore = true)
    Equipment toEntity(EquipmentRequestDTO equipmentRequestDTO);

    @Mapping(target = "id", ignore = true)
    void updateFromDTO(EquipmentRequestUpdateDTO equipmentRequestUpdateDTO, @MappingTarget Equipment equipment);
}
