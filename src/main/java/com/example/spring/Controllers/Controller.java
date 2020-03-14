package com.example.spring.Controllers;


import com.example.spring.model.Cats;
import com.example.spring.service.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class Controller {
    private DataService dataService;

    public Controller(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/cats")
    public List<Cats> getCats() throws SQLException {
        List<Cats> cats = dataService.callFunc();
        return cats;
    }

}
