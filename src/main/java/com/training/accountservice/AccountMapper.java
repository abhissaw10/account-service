package com.training.accountservice;

import com.training.accountservice.domin.Account;
import com.training.accountservice.dto.CreateAccountRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
unmappedTargetPolicy = ReportingPolicy.IGNORE,
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {

    public Account toAccount(CreateAccountRequestDto accountDto);
}
