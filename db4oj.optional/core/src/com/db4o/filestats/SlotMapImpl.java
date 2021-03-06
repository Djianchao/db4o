/* Copyright (C) 2010   Versant Inc.   http://www.db4o.com */

package com.db4o.filestats;

import java.util.*;

import com.db4o.foundation.*;
import com.db4o.internal.*;
import com.db4o.internal.slots.*;

/**
* @exclude
*/
@decaf.Ignore(decaf.Platform.JDK11)
public class SlotMapImpl implements SlotMap {

	private TreeIntObject<Slot> _slots = null;

	private final long _fileLength;

	public SlotMapImpl(long fileLength) {
		_fileLength = fileLength;
	}
	
	public void add(Slot slot) {
		_slots = Tree.add(_slots, new TreeIntObject<Slot>(slot.address(), slot));
	}
	
	public List<Slot> merged() {
		final List<Slot> mergedSlots = new ArrayList<Slot>();

		final ByRef<Slot> mergedSlot = ByRef.newInstance(new Slot(0,0));
		Tree.traverse(_slots, new Visitor4<TreeIntObject<Slot>>() {
			public void visit(TreeIntObject<Slot> node) {
				Slot curSlot = node._object;
				if(mergedSlot.value.address() + mergedSlot.value.length() == curSlot.address()) {
					mergedSlot.value = new Slot(mergedSlot.value.address(), mergedSlot.value.length() + curSlot.length());
				}
				else {
					mergedSlots.add(mergedSlot.value);
					mergedSlot.value = curSlot;
				}
			}
		});
		mergedSlots.add(mergedSlot.value);
		return mergedSlots;
	}
	
	public List<Slot> gaps(long length) {
		List<Slot> merged = merged();
		List<Slot> gaps = new ArrayList<Slot>();
		if(merged.size() == 0) {
			return gaps;
		}
		boolean isFirst = true;
		Slot prevSlot = null;
		for (Slot curSlot : merged) {
			if(isFirst) {
				prevSlot = curSlot;
				if(prevSlot.address() > 0) {
					gaps.add(new Slot(0, prevSlot.address()));
				}
				isFirst = false;
			}
			else {
				int gapStart = prevSlot.address() + prevSlot.length();
				gaps.add(new Slot(gapStart, curSlot.address() - gapStart));
				prevSlot = curSlot;
			}
		}
		int afterlast = prevSlot.address() + prevSlot.length();
		if(afterlast < length) {
			gaps.add(new Slot(afterlast, (int)(length - afterlast)));
		}
		return gaps;
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("SLOTS:\n");
		logSlots(merged(), str);
		str.append("GAPS:");
		logSlots(gaps(_fileLength), str);
		return str.toString();
	}
	
	private void logSlots(Iterable<Slot> slots, StringBuffer str) {
		int totalLength = 0;
		for (Slot gap : slots) {
			totalLength += gap.length();
			str.append(gap).append("\n");
		}
		str.append("TOTAL: ").append(totalLength).append("\n");
	}

}
