package com.example.SmartProcure.Controller;

import com.example.SmartProcure.Model.QuotationForm;
import com.example.SmartProcure.Model.RFQProducts;
import com.example.SmartProcure.Model.RequestForQuotation;
import com.example.SmartProcure.Repository.RFQProductsRepository;
import com.example.SmartProcure.Repository.RequestForQuotationRepository;
import com.example.SmartProcure.Service.QuotationService;
import com.example.SmartProcure.Service.RFQService;
import org.hibernate.engine.transaction.jta.platform.internal.ResinJtaPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/RFQ")
public class RequestForQuotationController {

    @Autowired
    RequestForQuotationRepository requestForQuotationRepository;

    //http://localhost:8080/api/RFQ/getAllRFQs
    @GetMapping("/getAllRFQs")
    public List<RequestForQuotation> getAllRFQs() {
        return requestForQuotationRepository.findAll();
    }

    @GetMapping("/getRFQ/{id}")
    public RequestForQuotation getRFQById(@PathVariable Long id) {
        return requestForQuotationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RFQ not found with id " + id));
    }

    //http://localhost:8080/api/RFQ/createRFQId
    @PostMapping("/createRFQId")
    public RequestForQuotation createRFQId(@RequestBody Map<String, String> requestBody) {
        RequestForQuotation requestForQuotation = new RequestForQuotation();
        String description = requestBody.get("description");
        requestForQuotation.setDescription(description);
        requestForQuotationRepository.save(requestForQuotation);
        System.out.println(requestForQuotation.getId());
        return requestForQuotation;
    }

    //http://localhost:8080/api/RFQ/addProductToRFQ/{rFQId}
    @PostMapping("/addProductToRFQ/{rFQId}")
    public ResponseEntity<String> addProductToRFQ(@PathVariable Long rFQId, @RequestBody RFQProducts rfqProduct) {
        try {
            RequestForQuotation requestForQuotation = requestForQuotationRepository.findById(rFQId)
                    .orElseThrow(() -> new RuntimeException("RFQ not found with id " + rFQId));
            System.out.println(rfqProduct.getProductId());
            System.out.println(rfqProduct.getQuantityRequired());
            System.out.println(rfqProduct.getNote());
            rfqProduct.setRequestForQuotation(requestForQuotation);
            List<RFQProducts> existingProducts = requestForQuotation.getRfqProducts();
            if(existingProducts==null){
                existingProducts = new ArrayList<>();
            }
            existingProducts.add(rfqProduct);
            requestForQuotation.setRfqProducts(existingProducts);
            requestForQuotationRepository.save(requestForQuotation);
            return new ResponseEntity<>("Product added successfully.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/api/RFQ/updateProduct/{rfqId}
    @PutMapping("/updateProduct/{rfqId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long rfqId, @RequestBody RFQProducts rfqProduct) {
        try {
            RequestForQuotation existingRFQ = requestForQuotationRepository.findById(rfqId)
                    .orElseThrow(() -> new RuntimeException("RFQ not found with id " + rfqId));

            List<RFQProducts> rfqProducts = existingRFQ.getRfqProducts();
            for (RFQProducts existing : rfqProducts) {
                if (existing.getProductId() == rfqProduct.getProductId()) {
                    existing.setQuantityRequired(rfqProduct.getQuantityRequired());
                    existing.setNote((rfqProduct.getNote()));
                    break;
                }
            }
            existingRFQ.setRfqProducts(rfqProducts);
            requestForQuotationRepository.save(existingRFQ);
            return new ResponseEntity<>("Products details updated successfully", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/api/RFQ/deleteRFQ/{rfqId}
    @DeleteMapping("/deleteRFQ/{rfqId}")
    public ResponseEntity<String> deleteRGQ(@PathVariable Long rfqId) {
        try {
            requestForQuotationRepository.deleteById(rfqId);
            return new ResponseEntity<>("RFQ delete successfully", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    http://localhost:8080/api/RFQ/deleteProductInRFQ/{rfqId}/{productId}
    @DeleteMapping("/deleteProductInRFQ/{rfqId}/{productId}")
    public ResponseEntity<String> deleteProductInRFQ(@PathVariable Long rfqId, @PathVariable Long productId) {
        try {
            RequestForQuotation requestForQuotation = requestForQuotationRepository.findById(rfqId)
                    .orElseThrow(() -> new RuntimeException("RFQ not found with id " + rfqId));

            Iterator<RFQProducts> iterator = requestForQuotation.getRfqProducts().iterator();
            while (iterator.hasNext()) {
                RFQProducts rfqProduct = iterator.next();
                if (rfqProduct.getProductId() == productId) {
                    iterator.remove();
                }
            }
            requestForQuotationRepository.save(requestForQuotation);
            return new ResponseEntity<>("Product removes from RFQ successfully.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}