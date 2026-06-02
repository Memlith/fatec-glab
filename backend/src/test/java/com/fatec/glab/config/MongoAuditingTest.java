package com.fatec.glab.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import com.fatec.glab.model.Equipment;
import com.fatec.glab.repository.EquipmentRepository;

@DataMongoTest
@Import(MongoAuditingConfig.class)
class MongoAuditingTest {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Test
    void shouldPopulateCreatedDateOnSave() {
        // arange
        Equipment equipment = new Equipment();
        equipment.setName("Server Rack 24U");
        
        assertNull(equipment.getCreatedDate(), "A data deve ser nula antes de salvar");

        // act
        Equipment savedEquipment = equipmentRepository.save(equipment);

        // assert
        assertNotNull(savedEquipment.getCreatedDate(), "O Spring Data deveria ter injetado a data de criação");
        assertNotNull(savedEquipment.getId(), "O ID também deve ter sido gerado");
    }
}