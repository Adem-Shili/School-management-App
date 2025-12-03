package com.project.schoolmanagementapp.services;

import com.project.schoolmanagementapp.DTO.request.AdminRequest;
import com.project.schoolmanagementapp.DTO.response.AdminResponse;

public interface IAdminService {

    AdminResponse register(AdminRequest request);
}
