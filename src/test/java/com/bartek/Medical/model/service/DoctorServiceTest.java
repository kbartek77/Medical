package com.bartek.Medical.model.service;

import com.bartek.Medical.service.DoctorService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.print.Doc;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
    @InjectMocks
    DoctorService doctorService;
}
