import os

JS_BASE_PATH = os.path.normpath("../frontend/warehouse/")
JS_SOURCE_PATH = os.path.join(JS_BASE_PATH, "src")
JS_INDEX_FILE = os.path.join(JS_SOURCE_PATH, "index.js")

variables_to_remove = [
    "registerServiceWorker",
    # "serverName",
]

def removeLineContainsString(string, filename=JS_INDEX_FILE):
    lines = []
    with open(filename, 'r') as read_fh:
        for line in read_fh:
            if line.find(string) < 0:
                lines.append(line)
            else:
                print("The line \n" + line + "\n will be deleted")

    with open(filename, 'w') as write_fh:
        write_fh.writelines(lines)


for variable in variables_to_remove:
    removeLineContainsString(variable)


def replace_in_file(replacementMap, filename)
    lines = []
    with open(filename, 'r') as read_fh:
        for line in read_fh:
            for replaceable, replacement in replacementMap:
                if line.find(string) < 0:
                    lines.append(line)
                else:
                    print("The line \n" + line + "\n will be deleted")

    with open(filename, 'w') as write_fh:
        write_fh.writelines(lines)



#\"(gc build/index.html) -replace '/static/css', '/css' | Out-File build/index.html\" 
#\"(gc build/index.html) -replace '/static/js', '/js' | Out-File build/index.html\"
#\\..\\src\\main\\resources\\ &&
# ..\\..\\src\\main\\resources\\index.html
#  ..\\..\\src\\main\\resources\\static\\index.html",
