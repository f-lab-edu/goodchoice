package com.flab.goodchoiceapi.item.interfaces;

import com.flab.goodchoiceapi.item.application.ItemFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemApiController {

    private final ItemFacade itemFacade;

    private final ItemDtoMapper itemDtoMapper;

    @PostMapping
    public ResponseEntity<ItemDto.RegisterResponse> registerItem(@RequestBody @Valid ItemDto.RegisterItemRequest request) {
        var itemCommand = itemDtoMapper.of(request);
        var itemToken = itemFacade.registerItem(itemCommand);
        var response = itemDtoMapper.of(itemToken);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{itemToken}")
    public ResponseEntity<ItemDto.Main> retrieve(@PathVariable("itemToken") String itemToken) {
        var itemInfo = itemFacade.retrieveItemInfo(itemToken);
        var response = itemDtoMapper.of(itemInfo);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto.Main>> retrieveAll() {
        var items = itemFacade.retrieveAllItemInfo();
        var response = itemDtoMapper.of(items);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{itemToken}")
    public ResponseEntity updateItem(@PathVariable("itemToken") String itemToken, @RequestBody @Valid ItemDto.UpdateItemRequest request) {
        var itemCommand = itemDtoMapper.of(request);
        itemFacade.updateItem(itemToken, itemCommand);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/change-end-of-sales")
    public ResponseEntity changeEndOfSaleItem(@RequestBody @Valid ItemDto.ChangeStatusItemRequest request) {
        var itemToken = request.getItemToken();
        itemFacade.changeEndOfSaleItem(itemToken);
        return ResponseEntity.ok("OK");
    }
}
