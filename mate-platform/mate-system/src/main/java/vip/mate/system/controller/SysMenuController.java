package vip.mate.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;
import vip.mate.core.common.api.Result;
import vip.mate.core.common.constant.MateConstant;
import vip.mate.core.web.controller.BaseController;
import vip.mate.core.web.util.CollectionUtil;
import vip.mate.system.entity.SysMenu;
import vip.mate.system.service.ISysMenuService;
import vip.mate.system.util.TreeUtil;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author xuzf
 * @since 2020-06-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys-menu")
public class SysMenuController extends BaseController {

    private final ISysMenuService sysMenuService;

    @GetMapping("/routes")
    public Result<?> routes() {
        return Result.data(sysMenuService.routes());
    }

    @GetMapping("/list")
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> search) {
        return Result.data(TreeUtil.list2Tree(sysMenuService.searchList(search), MateConstant.TREE_ROOT));
    }

    @GetMapping("/asyncList")
    public Result<?> asyncList(){
        return Result.data(TreeUtil.list2Tree(sysMenuService.list(), MateConstant.TREE_ROOT));
    }

    @PostMapping("/saveOrUpdate")
    public Result<?> saveOrUpdate(@Valid @RequestBody SysMenu sysMenu) {
        if (sysMenuService.saveAll(sysMenu)) {
            return Result.success("操作成功");
        }
        return Result.fail("操作失败");
    }

    @GetMapping("/info")
    public Result<?> getSysMenu(SysMenu sysMenu){
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysMenu::getId, sysMenu.getId());
        return Result.data(sysMenuService.getOne(queryWrapper));
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestParam String ids) {
        if(sysMenuService.removeByIds(CollectionUtil.stringToCollection(ids))) {
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }

    @PostMapping("/status")
    public Result<?> status(@RequestParam String ids, @RequestParam String status) {
        if (sysMenuService.status(ids, status)){
            return Result.success("批量修改成功");
        }
        return Result.fail("操作失败");
    }
}

