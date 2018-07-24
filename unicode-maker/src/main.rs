extern crate clap;

use clap::{App, Arg};
use std::fs::File;
use std::io::Read;

fn main() {
    let argmatches = App::new("unicode-maker")
        .version("0.1")
        .author("Hossein Noroozpour <hossein.noroozpour@gmail.com>")
        .about("Print numeric coded string.")
        .arg(
            Arg::with_name("file")
                .short("f")
                .long("file")
                .help("Sets text file")
                .takes_value(true)
                .required(true),
        )
        .arg(
            Arg::with_name("lang")
                .short("l")
                .long("lang")
                .help("Sets target language")
                .takes_value(true)
                .required(false)
                .default_value("java"),
        )
        .get_matches();
    let file = argmatches.value_of("file").expect("File is not specified.");
    let mut file = File::open(file).expect("File can not be opened.");
    let mut text = String::new();
    file.read_to_string(&mut text)
        .expect("File can not be readed.");
    let mut result = String::new();
    for c in text.chars() {
        result += &format!("\\u{:04X}", c as u16);
    }
    println!("{}", result);
}
