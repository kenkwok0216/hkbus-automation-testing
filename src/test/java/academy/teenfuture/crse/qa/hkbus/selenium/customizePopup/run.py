import os

def replace_chrome_in_content(original_file_name):
    # Get the current working directory
    current_dir = os.getcwd()
    original_file_path = os.path.join(current_dir, original_file_name)

    # Check if the original file exists
    if not os.path.isfile(original_file_path):
        print(f"File '{original_file_name}' not found in the current directory.")
        return

    # Define the new file names and corresponding replacements
    replacements = {
        'Firefox': 'Chrome',
        'Safari': 'Chrome',
        'Edge': 'Chrome'
    }
    
    # Read the original content
    with open(original_file_path, 'r') as original_file:
        content = original_file.read()

    # Create new files with the modified content
    for browser, old_name in replacements.items():
        # Replace "Chrome" with the current browser name in the content
        new_content = content.replace(old_name, browser)
        
        # Create a new file name by replacing "Chrome" in the original file name
        base_name, ext = os.path.splitext(original_file_name)
        new_file_name = f"{base_name.replace('Chrome', browser)}{ext}"
        new_file_path = os.path.join(current_dir, new_file_name)

        # Write the modified content to the new file
        with open(new_file_path, 'w') as new_file:
            new_file.write(new_content)

        print(f"New file created: {new_file_name}")

# Specify the name of your original file (must be in the current directory)
file_to_modify = 'EditBusOrderByChromeTest.java'  # Change this to your actual file name
replace_chrome_in_content(file_to_modify)