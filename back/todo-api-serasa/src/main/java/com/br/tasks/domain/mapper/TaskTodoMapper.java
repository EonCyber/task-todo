package com.br.tasks.domain.mapper;


import com.br.tasks.domain.entity.TaskTodo;
import com.br.tasks.domain.model.TaskTodoDO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskTodoMapper {

    TaskTodoMapper INSTANCE = Mappers.getMapper(TaskTodoMapper.class);
    @Mapping(source = "profileId", target = "profile.profileId")
    TaskTodo toEntity(TaskTodoDO profileDO);
    @Mapping(source = "profile.profileId", target = "profileId")
    TaskTodoDO toDomain(TaskTodo profile);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskFromDo(TaskTodoDO profile, @MappingTarget TaskTodo entity);

//    default AccountProfile map(Long profileId) {
//        if (profileId == null) {
//            return null;
//        }
//        AccountProfile profile = new AccountProfile();
//        profile.setProfileId(profileId);
//        return profile;
//    }
}