package com.br.tasks.domain.mapper;



import com.br.tasks.domain.entity.AccountProfile;
import com.br.tasks.domain.model.AccountProfileDO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountProfileMapper {

    AccountProfileMapper INSTANCE = Mappers.getMapper(AccountProfileMapper.class);
    @Mapping(source = "id", target = "profileId")
    AccountProfile toEntity(AccountProfileDO profileDO);
    @Mapping(source = "profileId", target = "id")
    AccountProfileDO toDomain(AccountProfile profile);
}