package tools.msm.b_tracking_list.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import tools.msm.b_tracking_list.domain.MsmElementRepository;
import tools.msm.b_tracking_list.domain.MsmIslandRepository;
import tools.msm.b_tracking_list.domain.MsmMonster;

import org.springframework.ui.Model;
import tools.msm.b_tracking_list.domain.MsmMonsterRepository;


@Controller
public class MsmMonsterController {

    MsmMonsterRepository mR;
    MsmIslandRepository iR;
    MsmElementRepository eR;

    public MsmMonsterController(MsmMonsterRepository mr,MsmIslandRepository ir,MsmElementRepository er) {
        this.mR = mr;
        this.eR = er;
        this.iR = ir;
    }

    // http://localhost:8080/mlist
    @GetMapping("/mlist")
    public String getMonsterList(
        Model model
    ) {
        model.addAttribute("monsters", mR.findAll());
        return "mlist"; // mlist.html
    }

    @GetMapping("/addMon")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getAddMonster(
        Model model
    ) {
        model.addAttribute("elements", eR.findAll());
        model.addAttribute("islands", iR.findAll());
        model.addAttribute("mon", new MsmMonster());
        return "addmon"; // addmon.html
    }
    
    @GetMapping("/editMon/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getEditMonster(
        @PathVariable("id") Long monId,
        Model model
    ) {
        model.addAttribute("elements", eR.findAll());
        model.addAttribute("islands", iR.findAll());
        model.addAttribute("mon", mR.findById(monId));
        return "editmon"; // editmon.html
    }

    @RequestMapping("/saveMon")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String reqSaveMon(
        @ModelAttribute MsmMonster monster
    ) {
        mR.save(monster);
        return "redirect:/mlist"; // back to monster list
    }

    @RequestMapping("/deleteMon/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String reqSaveMon(
        @PathVariable("id") Long monId
    ) {
        mR.deleteById(monId);
        return "redirect:/mlist"; // back to monster list
    }
    
}
