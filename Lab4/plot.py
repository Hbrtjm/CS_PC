import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from pathlib import Path

DATA_DIR = Path(".")

def read_amount_time(path: Path):
    df = pd.read_csv(path, header=None)
    if df.shape[1] >= 2:
        out = df.iloc[:, :2].copy()
        out.columns = ["amount", "time"]
    else:
        out = df.iloc[:, 0].astype(str).str.split(";", n=1, expand=True)
        out.columns = ["amount", "time"]
    out["amount"] = pd.to_numeric(out["amount"], errors="coerce")
    out["time"]   = pd.to_numeric(out["time"], errors="coerce")
    
    return out

def mean_by_amount(df: pd.DataFrame) -> pd.DataFrame:
    return (df.groupby("amount", as_index=False)["time"].mean().sort_values("amount"))

def plot_run(run: int, entities=("producers", "consumers"), names={"producers": "Producenci", "consumers": "Konsumenci"}, logy=True, save=True):
    plt.figure(figsize=(9, 5))
    plotted_any = False

    for entity in entities:
        brut_path = DATA_DIR / f"{run}_{entity}_time_brut.csv"
        sol_path  = DATA_DIR / f"{run}_{entity}_time_solution.csv"

        brut = read_amount_time(brut_path)
        sol  = read_amount_time(sol_path)
        
        if logy:
            brut["time"] = np.log10(brut["time"])
            sol["time"] = np.log10(sol["time"])
        
        if brut is not None:
            gb = mean_by_amount(brut)
            plt.plot(gb["amount"], gb["time"], "o--",
                     label=f"{names.get(entity, entity)} — Rozwiązanie naiwne")
            plotted_any = True

        if sol is not None:
            gs = mean_by_amount(sol)
            plt.plot(gs["amount"], gs["time"], "o-",
                     label=f"{names.get(entity, entity)} — Priorytetyzacja")
            plotted_any = True

    if logy:
        ylabel = "Średni czas oczekiwania [ns] (skala log)"
    else:
        ylabel = "Średni czas oczekiwania [ns]"

    plt.title(f"Średni czas względem wielkości porcji — Symulacja nr. {run}")
    plt.xlabel("Amount")
    plt.ylabel(ylabel)
    if plotted_any:
        plt.legend()
    plt.grid(True, which="both", axis="both")
    plt.tight_layout()

    if save:
        out = DATA_DIR / f"run_{run}_avg_time_by_amount.png"
        plt.savefig(out)
        
    plt.show()
    
def main():
    runs = [0, 1, 2]
    entities = ["producers", "consumers"]
    names = {"producers": "Producenci", "consumers": "Konsumenci"}

    for r in runs:
        plot_run(r, entities=entities, names=names, logy=True, save=True)

if __name__ == "__main__":
    main()
