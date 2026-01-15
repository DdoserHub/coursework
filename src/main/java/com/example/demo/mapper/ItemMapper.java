package com.example.demo.mapper;

import com.example.demo.dto.ItemDTO.ItemPartialUpdateDTO;
import com.example.demo.dto.ItemDTO.ItemRequestDTO;
import com.example.demo.dto.ItemDTO.ItemResponseDTO;
import com.example.demo.entity.Item;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item toItem(ItemRequestDTO itemRequestDTO);

    ItemResponseDTO toItemResponseDTO(Item item);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateRequestDTO(ItemPartialUpdateDTO itemPartialUpdateDTO, @MappingTarget Item item);
}
