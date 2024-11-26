package org.microservice.screen.services.screens;

import org.microservice.screen.entities.Screen;
import org.microservice.screen.models.screens.ScreenCreateReq;
import org.microservice.screen.models.screens.ScreenUpdateReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScreenService {
    Page<Screen> getAll(Pageable pageable);
    
    Screen createScreenAndSeats(ScreenCreateReq req);
    
    Screen getById(Long id);
    
    Screen updateScreen(Long id, ScreenUpdateReq req);
}
