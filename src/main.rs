// The $ delimiter is supposed to go to the bottom of the sort.
// I'm too much of a rust noobie to figure this out, so just cheat by replacing the $ character with a character with an ASCII value greater than all the other ones.
const DELIMITER: char = '~';
const OUTPUT: &str = "endrtednedd:/os....cp.rnnn.rhhps/.tt~sfeaiaaofd.ow.otooapa.asu./thhse";

fn main() {
    reverse(OUTPUT);
}

// This is all unnecessary and came from when I attempted to forward-generate the password.
// Which isn't the problem.
/*
fn rotate(word: &str) -> String {
    (&word[1..]).to_string() + &word[0..1]
}

fn generate_rotation_table(word: &str) -> Vec<String> {
    let word = word.to_string() + "$";
    let mut current = word.clone();

    let mut table = Vec::new();
    for _ in word.chars() {
        table.push(current.clone());
        current = rotate(&current);
    }

    table
}

fn generate_sort_table(rotation_table: &Vec<String>) -> Vec<String> {
    let mut sort_table = rotation_table.clone();
    sort_table.sort();
    // We expect the end marker ($) to be at the end.
    // This assumes the rotation starting with $ is first.
    sort_table.rotate_left(1);
    sort_table
}
 */

fn reverse(output: &str) {
    let len = output.len();

    // Construct a len x len 2D vector (of characters).
    // This is the sort table, which we will reconstruct.
    let template = vec![' '; len];
    let mut sort_table = Vec::new();
    for _ in 0..len {
        sort_table.push(template.clone());
    }

    // Populate ending characters.
    // We're given the final output, which consists of the ending characters in order.
    for (i, c) in output.chars().enumerate() {
        sort_table[i][len - 1] = c;
    }

    // The main observation is if you "rotate" the sort table and re-sort it, you'll end up with the sort table.
    // We'll reconstruct all the characters, column by column, going from left to right.
    // We reconstruct each column by rotating right (so column len-1 becomes column 0) and re-sort.
    // We then copy whichever column we're currently on back to our original table.
    for i in 0..len-1 {
        // Table with the first i+1 characters correctly filled.
        let i_table = rotate_right_and_sort(&sort_table);
        for (j, row) in i_table.iter().enumerate() {
            sort_table[j][i] = row[i];
        }
    }

    for word in sort_table {
        let x: String = word.iter().collect();
        println!("{:?}", x);
    }
}

fn rotate_right_and_sort(reverse_table: &Vec<Vec<char>>) -> Vec<Vec<char>> {
    let mut reverse_table = reverse_table.clone();
    for row in reverse_table.iter_mut() {
       row.rotate_right(1)
    }

    reverse_table.sort();

    reverse_table
}