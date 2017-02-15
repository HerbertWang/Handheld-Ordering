package com.alex.food.utils;

import java.math.BigDecimal;

import com.alex.food.bean.ItemMasterDto;
import com.alex.food.bean.TxSalesDetailDto;

public class ItemMasterDtoToTxSalesDetailDto {
	public static TxSalesDetailDto getTxSalesDetailDto(ItemMasterDto dto,
			boolean isItemOnHold, int seqNo, int itemOrderRunningIndex,
			int itemSetRunningIndex, String subItemLevel) {
		TxSalesDetailDto txDto = new TxSalesDetailDto();
		if (null != dto.getItemPrice()) {
			txDto.setAmount(getAmount(dto.getItemQty(), dto.getItemPrice()));
		}
		if (null != dto.getItemPoint()) {
			txDto.setAmountPoint(getAmountPoint(dto.getItemPoint(),
					dto.getItemQty()));
		}
		if (null != dto.getCategoryId()) {
			txDto.setCategoryId(dto.getCategoryId());
		}
		if (null != dto.getDepartmentId()) {
			txDto.setDepartmentId(dto.getDepartmentId());
		}
		if (null != dto.getDepartmentName()) {
			txDto.setDepartmentName(dto.getDepartmentName());
		}
		txDto.setDisplayIndexString(String.valueOf(itemOrderRunningIndex));
		txDto.setIsItemOnHold(String.valueOf(isItemOnHold));
		if (null != dto.getIsItemShow()) {
			txDto.setIsItemShow(dto.getIsItemShow());
		}
		if (null != dto.getIsItemShowInKitchenChecklist()) {
			txDto.setIsItemShowInKitchenChecklist(dto
					.getIsItemShowInKitchenChecklist());
		}
		if (null != dto.getIsLimitedItem()) {
			txDto.setIsLimitedItem(dto.getIsLimitedItem());
		}
		if (null != dto.getIsModifier()) {
			txDto.setIsModifier(dto.getIsModifier());
		}
		if (null != dto.getIsModifierConcatToParent()) {
			txDto.setIsModifierConcatToParent(dto.getIsModifierConcatToParent());
		}
		if (null != dto.getIsNoPointEarnItem()) {
			txDto.setIsNoPointEarnItem(dto.getIsNoPointEarnItem());
		}
		if (null != dto.getIsNonDiscountItem()) {
			txDto.setIsNonDiscountItem(dto.getIsNonDiscountItem());
		}
		if (null != dto.getIsNonServiceChargeItem()) {
			txDto.setIsNonServiceChargeItem(dto.getIsNonServiceChargeItem());
		}
		if (null != dto.getIsNonTaxableItem()) {
			txDto.setIsNonTaxableItem(dto.getIsNonTaxableItem());
		}
		if (null != dto.getIsPointPaidItem()) {
			txDto.setIsPointPaidItem(dto.getIsPointPaidItem());
		}
		if (null != dto.getIsPriceInPercentage()) {
			txDto.setIsPriceInPercentage(dto.getIsPriceInPercentage());
		}
		if (null != dto.getIsPriceShow()) {
			txDto.setIsPriceShow(dto.getIsPriceShow());
		}
		if (null != dto.getIsPrintLabel()) {
			txDto.setIsPrintLabel(dto.getIsPrintLabel());
		}
		if (null != dto.getIsPrintLabelTakeaway()) {
			txDto.setIsPrintLabelTakeaway(dto.getIsPrintLabelTakeaway());
		}
		if (null != dto.getItemCode()) {
			txDto.setItemCode(dto.getItemCode());
		}
		if (null != dto.getItemId()) {
			txDto.setItemId(dto.getItemId());
		}
		if (null != dto.getItemName()) {
			txDto.setItemName(dto.getItemName());
		}
		if (null != dto.getItemNameAlt()) {
			txDto.setItemNameAlt(dto.getItemNameAlt());
		}
		if (null != dto.getItemNameAlt2()) {
			txDto.setItemNameAlt2(dto.getItemNameAlt2());
		}
		if (null != dto.getItemNameAlt3()) {
			txDto.setItemNameAlt3(dto.getItemNameAlt3());
		}
		if (null != dto.getItemNameAlt4()) {
			txDto.setItemNameAlt4(dto.getItemNameAlt4());
		}
		txDto.setItemOrderRunningIndex(String.valueOf(itemOrderRunningIndex));
		txDto.setItemSetRunningIndex(String.valueOf(itemSetRunningIndex));
		if (null != dto.getPrinterName()) {
			txDto.setLocalPrinterName(dto.getPrinterName());
		}
		if (null != dto.getPrinterName2()) {
			txDto.setLocalPrinterName2(dto.getPrinterName2());
		}
		if (null != dto.getPrinterName3()) {
			txDto.setLocalPrinterName3(dto.getPrinterName3());
		}
		if (null != dto.getPrinterName4()) {
			txDto.setLocalPrinterName4(dto.getPrinterName4());
		}
		if (null != dto.getPrinterName5()) {
			txDto.setLocalPrinterName5(dto.getPrinterName5());
		}
		if (null != dto.getItemPoint()) {
			txDto.setPoint(dto.getItemPoint());
		}
		if (null != dto.getItemPrice()) {
			txDto.setPrice(dto.getItemPrice());
		}
		if (null != dto.getItemQty()) {
			txDto.setQty(dto.getItemQty());
		}
		if (null != dto.getItemSetSeq()) {
			txDto.setSeqNo(String.valueOf(seqNo));
		}
		if (null != dto.getTakeawaySurcharge()) {
			txDto.setTakeawaySurcharge(dto.getTakeawaySurcharge());
		}
		txDto.setSubItemLevel(subItemLevel);
		return txDto;
	}

	private static String getAmount(String qty, String price) {
		BigDecimal mQty = new BigDecimal(qty);
		BigDecimal mPrice = new BigDecimal(price);
		BigDecimal amount = mQty.multiply(mPrice);
		return amount.toString();
	}

	private static String getAmountPoint(String point, String qty) {
		BigDecimal mPoint = new BigDecimal(point);
		BigDecimal mQty = new BigDecimal(qty);
		BigDecimal amountPoint = mPoint.multiply(mQty);
		return amountPoint.toString();
	}
}
