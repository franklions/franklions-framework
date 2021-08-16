package com.franklions.example.mapper;

import com.franklions.example.domain.entity.OAuthAccessToken;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface OAuthAccessTokenMapper extends Mapper<OAuthAccessToken> {
}