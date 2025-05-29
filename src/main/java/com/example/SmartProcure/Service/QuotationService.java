package com.example.SmartProcure.Service;

import com.example.SmartProcure.Model.*;
import com.example.SmartProcure.Repository.CartRepository;
import com.example.SmartProcure.Repository.QuotationFormRepository;
import com.example.SmartProcure.Repository.QuotationItemRepository;
import com.example.SmartProcure.Repository.RequestForQuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuotationService {
    @Autowired
    QuotationFormRepository quotationFormRepository;
    @Autowired
    QuotationItemRepository quotationItemRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;

    @Autowired
    RequestForQuotationRepository requestForQuotationRepository;


    private static final String UPLOAD_DIR = "uploads/";

    public List<QuotationForm> getAllQuotations(){
         return quotationFormRepository.findAll();
    }

//    public QuotationForm getByVendorTimeStamp(Long formId){
//        System.out.println("QS line 41: "+formId);
//        List<QuotationForm> forms = quotationFormRepository.findAll();
//        QuotationForm res = new QuotationForm();
//        for(QuotationForm form : forms){
//            System.out.println("QS line 45: "+form.getId());
//            if(Objects.equals(form.getId(), formId)) {
//                res = form;
//            }
//        }
//        System.out.println("QS line 50: "+res.getId());
//        return res;
//    }
//    public QuotationForm createQuotationForm(Long vendorId){
//        System.out.println("QS line 54: "+vendorId);
//
//        QuotationForm form = new QuotationForm();
//        form.setVendorId(vendorId);
//        form.setCreatedAt(LocalDateTime.now());
//        form.setItems(new ArrayList<>());
//
//        Cart cart = cartService.getCartItemsByVendorId(vendorId);
//        List<ProductData> products = cart.getProducts();
//        for(ProductData product : products) {
//            QuotationItems item = new QuotationItems();
//            item.setQuotationForm(form);
//            item.setProductName(product.getProductName());
//            item.setAlias(product.getAlias());
//            item.setMake(product.getMake());
//            item.setQuantityRequired(product.getQuantityRequired());
//            item.setNote(product.getNote());
//            form.getItems().add(item);
//        }
//        return quotationFormRepository.save(form);
//    }


    public QuotationForm createQuotationForm(Long vendorId, Long rfqId){
        QuotationForm quotationForm = new QuotationForm();
        RequestForQuotation requestForQuotation = requestForQuotationRepository.getReferenceById(rfqId);
        quotationForm.setDescription(requestForQuotation.getDescription());
        quotationForm.setVendorId(vendorId);
        quotationForm.setCreatedAt(LocalDateTime.now());
        quotationForm.setItems(new ArrayList<>());

        List<RFQProducts> rfqProducts = requestForQuotation.getRfqProducts();
        for(RFQProducts rfqProduct: rfqProducts){
            QuotationItems item = new QuotationItems();
            item.setProductId(rfqProduct.getProductId());
            item.setQuantityRequired(rfqProduct.getQuantityRequired());
            item.setNote(rfqProduct.getNote());
            item.setQuotationForm(quotationForm);
            quotationForm.getItems().add(item);
        }
        return  quotationFormRepository.save(quotationForm);
    }

    public ResponseEntity<String> submitQuotationForm(Long formId, String submittedByName, String submittedByPhone, List<QuotationItems> items){
        try{
            Optional<QuotationForm> formOptional = quotationFormRepository.findById(formId);
            if (formOptional.isPresent()) {
                QuotationForm form = formOptional.get();
                form.setSubmittedByName(submittedByName);
                form.setSubmittedByPhone(submittedByPhone);
                form.setSubmittedAt(LocalDateTime.now());

                for (QuotationItems item : items) {
                    item.setQuotationForm(form);
                    quotationItemRepository.save(item);
                }
                quotationFormRepository.save(form);
            }
            return ResponseEntity.ok("Form Successfully submitted.");
        }
        catch(Exception ex){
            return  new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //for inventory manager to attach any doc to a particular item
//    public ResponseEntity<String> uploadFileToItem(Long quotationItemId, MultipartFile file){
//        try{
//            String filePath = UPLOAD_DIR + file.getOriginalFilename();
//            Files.createDirectories(Paths.get(UPLOAD_DIR));
//            Files.write(Paths.get(filePath), file.getBytes());
//
//            QuotationItems item = quotationItemRepository.findById(quotationItemId)
//                    .orElseThrow(() -> new RuntimeException("Quotation Item not found"));
//
//            item.setDocumentPath(filePath);
//            quotationItemRepository.save(item);
//
//            return ResponseEntity.ok("File uploaded successfully: " +filePath);
//        }
//        catch(IOException ex){
//            return  ResponseEntity.internalServerError().body("File upload failed:" + ex.getMessage());
//        }
//        catch(Exception ex){
//            return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
//        }
//    }

    public ResponseEntity<String> uploadFileToVendor(Long quotationFormId, MultipartFile file){
        try{
            String filePath = UPLOAD_DIR + file.getOriginalFilename();
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            Files.write(Paths.get(filePath), file.getBytes());

            QuotationForm form = quotationFormRepository.findById(quotationFormId)
                    .orElseThrow(() -> new RuntimeException("Quotation Item not found"));

            form.setDocumentPath(filePath);
            quotationFormRepository.save(form);

            return ResponseEntity.ok("File uploaded successfully: " +filePath);
        }
        catch(IOException ex){
            return  ResponseEntity.internalServerError().body("File upload failed:" + ex.getMessage());
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
        }
    }
}
