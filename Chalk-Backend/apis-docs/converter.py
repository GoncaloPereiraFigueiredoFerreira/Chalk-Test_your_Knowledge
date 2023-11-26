import yaml, os, sys

def load_paths(paths):
    new_paths = dict()
    for path,path_content in paths.items():
        with open("paths" + path + ".yml", "r") as pathfile:
            new_paths[path] = yaml.safe_load(pathfile)
    return new_paths

def update_refs(data):
    if isinstance(data, dict):
        for key, value in data.items():
            if key == '$ref':
                token = data[key].split("/")[-1]
                data[key] = "#/components/schemas/" + token
            else:
                update_refs(value)
    elif isinstance(data, list):
        for item in data:
            update_refs(item)

#def update_refs(data):
#    if isinstance(data, dict):
#        for key, value in data.items():
#            if key == "$ref":
#                tokens = value.split("/")
#                name = (tokens[-1]).removesuffix(".yml").removesuffix(".yaml")
#                #data[key] = "./" + name + ".yml"
#                data[key] = "./_index.yml#/" + name
#            elif isinstance(value, (dict, list)):
#                if key == "paths":
#                    paths_update_refs(value)
#                else:
#                    if key == "schemas":
#                        prefix = "./"
#                    aux_update_refs(value, prefix)
#    elif isinstance(data, list):
#        for item in data:
#            aux_update_refs(item, prefix)

def load_schemas(schemas):
    new_schemas = dict()
    for schema, schema_ref in schemas.items():
        schema_ref = schema_ref["$ref"]
        with open("components/schemas/" + schema_ref, "r") as schemafile:
            schema_content = yaml.safe_load(schemafile)
            update_refs(schema_content)
            new_schemas[schema] = schema_content
    return new_schemas

infile_contents = None
with open("main.yaml", "r") as infile:
    infile_contents = yaml.safe_load(infile)

new_paths = load_paths(infile_contents["paths"])
new_schemas = None
with open("components/schemas/_index.yml", "r") as indexfile:
    schemas = yaml.safe_load(indexfile)
    new_schemas = load_schemas(schemas)

update_refs(new_paths)

infile_contents["paths"] = new_paths
infile_contents["components"]["schemas"] = new_schemas

outfile = open("newmain.yaml", "w")
yaml.dump(infile_contents, outfile)
outfile.close()
