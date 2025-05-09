/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ghidra.app.util.bin.format.omf.omf51;

import java.io.IOException;

import ghidra.app.util.bin.BinaryReader;
import ghidra.app.util.bin.format.omf.*;
import ghidra.program.model.data.*;
import ghidra.util.exception.DuplicateNameException;

public class Omf51ModuleEnd extends OmfRecord {

	private OmfString moduleName;
	private byte regMsk;

	/**
	 * Creates a new {@link Omf51ModuleEnd} record
	 * 
	 * @param reader A {@link BinaryReader} positioned at the start of the record
	 * @throws IOException if an IO-related error occurred
	 */
	public Omf51ModuleEnd(BinaryReader reader) throws IOException {
		super(reader);
	}

	@Override
	public void parseData() throws IOException, OmfException {
		moduleName = OmfUtils.readString(dataReader);
		dataReader.readNextByte();
		dataReader.readNextByte();
		regMsk = dataReader.readNextByte();
		dataReader.readNextByte();
	}

	/**
	 * {@return the register mask}
	 */
	public byte getRegisterMask() {
		return regMsk;
	}

	@Override
	public DataType toDataType() throws DuplicateNameException, IOException {
		StructureDataType struct = new StructureDataType(Omf51RecordTypes.getName(recordType), 0);
		struct.add(BYTE, "type", null);
		struct.add(WORD, "length", null);
		struct.add(moduleName.toDataType(), moduleName.getDataTypeSize(), "name", null);
		struct.add(WORD, "padding", null);
		struct.add(BYTE, "REG MSK", null);
		struct.add(BYTE, "padding", null);
		struct.add(BYTE, "checksum", null);

		struct.setCategoryPath(new CategoryPath(OmfUtils.CATEGORY_PATH));
		return struct;
	}
}
