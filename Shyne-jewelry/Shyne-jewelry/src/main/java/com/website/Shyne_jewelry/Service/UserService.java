package com.website.Shyne_jewelry.Service;

import com.website.Shyne_jewelry.dto.AuthResponse;
import com.website.Shyne_jewelry.dto.LoginDTO;
import com.website.Shyne_jewelry.dto.RegisterDTO;

public interface UserService {

AuthResponse registerUser(RegisterDTO dto);

AuthResponse registerAdmin(RegisterDTO dto);

AuthResponse login(LoginDTO dto);



}
