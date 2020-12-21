package com.proyecto.config;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
@EnableScheduling
public class ScheduledJob {}