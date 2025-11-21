package com.fatec.glab.mapper;

import com.fatec.glab.dto.equipment.EquipmentRequestDTO;
import com.fatec.glab.dto.equipment.EquipmentRequestUpdateDTO;
import com.fatec.glab.dto.equipment.EquipmentResponseDTO;
import com.fatec.glab.model.Equipment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {

    EquipmentResponseDTO toDTO(Equipment equipment);

    List<EquipmentResponseDTO> toDTO(List<Equipment> equipmentList);

    Equipment toEntity(EquipmentRequestDTO equipmentRequestDTO);

    void updateFromDTO(EquipmentRequestUpdateDTO equipmentRequestUpdateDTO, Equipment equipment);
}
