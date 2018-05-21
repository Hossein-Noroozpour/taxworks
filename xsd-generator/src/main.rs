use std::fs::File;
use std::io::Write;

fn convert_to_string(i: i32) -> String {
    if i == 0 {
        return "A".to_string();
    } else if i < 0 {
        panic!("Unacceptable number");
    }
    const BASE: i32 = 26i32;
    let mut result = String::new();
    let mut dom = i;
    loop {
        if dom < 1 { break; }
        let rem = dom % BASE;
        result += &((rem + 'A' as i32) as u8 as char).to_string();
        dom /= BASE;
    }
    result
}

fn main() {
    let mut file = File::create("1.xsd").expect("Failed to open XSD file.");
    write!(&mut file, "<xs:schema attributeFormDefault=\"unqualified\" elementFormDefault=\"qualified\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n").unwrap();
    write!(&mut file, "  <xs:element name=\"content\">\n").unwrap();
    write!(&mut file, "    <xs:complexType>\n").unwrap();
    write!(&mut file, "      <xs:all>\n").unwrap();
    for i in 1..103 {
        write!(&mut file, "        <xs:element type=\"xs:string\" name=\"{}\" minOccurs=\"0\" maxOccurs=\"1\"/><!--{}-->\n", convert_to_string(i), i).unwrap();
    }
    write!(&mut file, "      </xs:all>\n").unwrap();
    write!(&mut file, "    </xs:complexType>\n").unwrap();
    write!(&mut file, "  </xs:element>\n").unwrap();
    write!(&mut file, "</xs:schema>\n").unwrap();
    let mut file = File::create("1.xml").expect("Failed to open XML file.");
    write!(&mut file, "<content>\n").unwrap();
    for i in 1..103 {
        let e = convert_to_string(i);
        write!(&mut file, "  <{}>{}-{}<{}><!--{}-->\n", e, i, e, e, i).unwrap();
    }
    write!(&mut file, "</content>\n").unwrap();
}
