
fn convert_to_string(i: i32) -> String {
    if i == 0 {
        return "A".to_string();
    } else if i < 0 {
        panic!("Unacceptable number");
    }
    const BASE: i32 = 52i32;
    const INDC: i32 = 26i32;
    let mut result = String::new();
    let mut dom = i;
    loop {
        if dom < 1 { break; }
        let rem = dom % BASE;
        if rem < INDC {
            result += &((rem + 'A' as i32) as u8 as char).to_string();
        } else {
            result += &(((rem - 26) + 'a' as i32) as u8 as char).to_string();
        }
        dom /= BASE;
    }
    result
}

fn main() {
    println!("<xs:schema attributeFormDefault=\"unqualified\" elementFormDefault=\"qualified\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">");
    println!("  <xs:element name=\"content\">");
    println!("    <xs:complexType>");
    println!("      <xs:sequence>");
    for i in 1..103 {
        println!("        <xs:element type=\"xs:string\" name=\"{}\"/><!--{}-->", convert_to_string(i), i);
    }
    println!("      </xs:sequence>");
    println!("    </xs:complexType>");
    println!("  </xs:element>");
    println!("</xs:schema>");
}
