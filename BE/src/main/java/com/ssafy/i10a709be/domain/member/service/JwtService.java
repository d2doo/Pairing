package com.ssafy.i10a709be.domain.member.service;

import java.util.List;

public interface JwtService {
    List<String> refresh(String memberId);
}
