package com.example.SmartProcure.Controller;

import com.example.SmartProcure.DTO.QuotationFormDTO;
import com.example.SmartProcure.Model.QuotationForm;
import com.example.SmartProcure.Model.QuotationItems;
import com.example.SmartProcure.Repository.QuotationFormRepository;
import com.example.SmartProcure.Repository.QuotationItemRepository;
import com.example.SmartProcure.Service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/quotation")
public class QuotationController {
    @Autowired
    QuotationService quotationService;
    @Autowired
    QuotationFormRepository quotationFormRepository;

    @GetMapping("/getAll")
    public List<QuotationForm> getAllQuotations(){
        return quotationService.getAllQuotations();
    }

    //http://localhost:8080/api/quotation/{formId}
    @GetMapping("/{formId}")
    public ResponseEntity<QuotationFormDTO> getQuotationForm(@PathVariable Long formId) {
        QuotationForm form = quotationFormRepository.findById(formId)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        return ResponseEntity.ok(new QuotationFormDTO(form));
    }

//    //http://localhost:8080/api/quotation/create/{vendorId}
//    @PostMapping("/create/{vendorId}")
//    public String createQuotationForm(@PathVariable Long vendorId){
//        QuotationForm form = quotationService.createQuotationForm(vendorId);
//        return "http://localhost:5173/quotation/"+ vendorId + "/" + form.getCreatedAt() + "/" +form.getId();
//    }

    @PostMapping("/create/{rFQId}/{vendorId}")
    public String createQuotationForm(@PathVariable Long rFQId, @PathVariable Long vendorId){
        QuotationForm form = quotationService.createQuotationForm(vendorId, rFQId);
        return "http://localhost:5173/quotation/"+ vendorId + "/" + form.getCreatedAt() + "/" +form.getId();
    }
    //http://localhost:8080/api/quotation/submit/{formId}
//    @PostMapping("/submit/{formId}")
//    public ResponseEntity<String> submitQuotationForm(@PathVariable Long formId, @RequestBody Map<String, Object> payload){
//        String submittedByName = (String) payload.get("submittedByName");
//        String submittedByPhone = (String) payload.get("submittedByPhone");
//        List<LinkedHashMap<String, Object>> itemsMap = (List<LinkedHashMap<String, Object>>) payload.get("items");
//        List<QuotationItems> items = itemsMap.stream().map(itemMap -> {
//            QuotationItems item = new QuotationItems();
//            item.setProductName((String) itemMap.get("productName"));
//            item.setAlias((String) itemMap.get("alias"));
//            item.setMake((String) itemMap.get("make"));
//            item.setQuantityRequired((String) itemMap.get("quantityRequired"));
//            item.setNote((String) itemMap.get("note"));
//            item.setRate(Double.parseDouble(itemMap.get("rate").toString()));
//
//            // Handle optional fields
//            if (itemMap.get("deliveryDate") != null) {
//                item.setDeliveryDate(java.sql.Date.valueOf((String) itemMap.get("deliveryDate")));
//            }
//            item.setAdditionalNotes((String) itemMap.get("additionalNotes"));
//            item.setDocumentPath((String) itemMap.get("documentPath"));
//
//            return item;
//        }).toList();
//        return quotationService.submitQuotationForm(formId, submittedByName, submittedByPhone, items);
//    }

//    @PostMapping("/upload/{quotationItemId}")
//    public ResponseEntity<String> uploadFileToItem(@PathVariable Long quotationItemId, @RequestBody MultipartFile file){
//        return quotationService.uploadFileToItem(quotationItemId, file);
//    }

    @PostMapping("/upload/{quotationFormId}")
    public ResponseEntity<String> uploadFileToVendor(@PathVariable Long quotationFormId, @RequestBody MultipartFile file){
        return quotationService.uploadFileToVendor(quotationFormId, file);
    }


}
