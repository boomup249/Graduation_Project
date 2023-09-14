import importlib

def install_and_import(package):
    try:
        importlib.import_module(package)
    except ImportError:
        import subprocess
        subprocess.check_call(["pip", "install", package])
    finally:
        globals()[package] = importlib.import_module(package)

try:
    import requests
except ImportError:
    install_and_import("requests")